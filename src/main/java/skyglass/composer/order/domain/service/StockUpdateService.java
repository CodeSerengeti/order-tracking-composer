package skyglass.composer.order.domain.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skyglass.composer.order.domain.model.StockMessage;
import skyglass.composer.order.domain.repository.StockTransactionBean;
import skyglass.composer.order.exceptions.ClientException;
import skyglass.composer.order.exceptions.InvalidTransactionStateException;
import skyglass.composer.order.exceptions.TransactionRollbackException;

@Service
public class StockUpdateService {

	@Autowired
	private StockUpdateConnector stockUpdateConnector;

	private StockUpdateProcessor stockUpdateProcessor;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private StockUpdateBean stockUpdateBean;

	@Autowired
	private StockTransactionBean stockTransactionBean;

	@PostConstruct
	public void init() throws Exception {
		this.stockUpdateProcessor = new StockUpdateProcessor(dataSource, stockUpdateConnector);
	}

	public void replayTransactions(StockMessage stockMessage) {
		stockTransactionBean.deleteCommittedTransactions(stockMessage.getItem(), stockMessage.getFrom());
		stockTransactionBean.deleteCommittedTransactions(stockMessage.getItem(), stockMessage.getTo());
		List<StockMessage> stockMessages = stockTransactionBean.findPendingMessages(stockMessage.getItem(), stockMessage.getFrom());
		stockMessages.addAll(stockTransactionBean.findPendingMessages(stockMessage.getItem(), stockMessage.getTo()));
		stockMessages.stream().forEach(s -> replayTransaction(s));
	}

	private void replayTransaction(final StockMessage stockMessage) {
		boolean success = false;
		try {
			if (stockMessage.shouldUpdateStock()) {
				stockUpdateProcessor.updateStock(stockMessage.getItem(), stockMessage.getFrom(),
						() -> stockUpdateBean.changeStockFrom(stockMessage));

				stockUpdateProcessor.updateStock(stockMessage.getItem(), stockMessage.getTo(),
						() -> stockUpdateBean.changeStockTo(stockMessage));
			}
			success = true;
		} catch (TransactionRollbackException e) {
			try {
				stockUpdateProcessor.updateStock(stockMessage.getItem(), stockMessage.getFrom(),
						() -> stockUpdateBean.revertStockFrom(stockMessage));
				stockUpdateProcessor.updateStock(stockMessage.getItem(), stockMessage.getTo(),
						() -> stockUpdateBean.revertStockTo(stockMessage));

				success = true;
			} catch (TransactionRollbackException e2) {
				throw new InvalidTransactionStateException("Programming Error during Transaction Rollback. Please, fix the code!", e);
			} catch (IOException | SQLException ex) {
				throw new ClientException(ex);
			}
		} catch (IOException | SQLException ex) {
			throw new ClientException(ex);
		} finally {
			if (success) {
				stockTransactionBean.commitTransaction(stockMessage);
			}
		}
	}

}