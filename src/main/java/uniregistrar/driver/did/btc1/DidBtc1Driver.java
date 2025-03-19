package uniregistrar.driver.did.btc1;

import com.google.common.base.Preconditions;
import info.weboftrust.btctxlookup.bitcoinconnection.BitcoindRPCBitcoinConnection;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bitcoinj.core.Context;
import org.bitcoinj.wallet.Wallet;
import uniregistrar.driver.Driver;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

public class DidBtc1Driver implements Driver {

	private static final Logger log = LogManager.getLogger(DidBtc1Driver.class);

	private BitcoindRPCBitcoinConnection rpcClientTestNet;
	private BitcoindRPCBitcoinConnection rpcClientMainNet;
	private BitcoindRPCBitcoinConnection rpcClientRegtest;

	private Wallet utxoWalletMainnet;
	private Wallet utxoWalletTestnet;
	private Wallet utxoWalletRegtestnet;

	private File utxoWalletMainnetFile;
	private File utxoWalletTestnetFile;
	private File utxoWalletRegtestnetFile;

	private Context contextMainnet;
	private Context contextTestnet;
	private Context contextRegtest;

	public DidBtc1Driver() {
		this(new Properties());
	}

	public DidBtc1Driver(Properties props) {

		Thread.currentThread().setName("DidBTC1Driver-MainThread");
		Preconditions.checkNotNull(props, "Driver properties cannot be null!");

		log.debug("Creating new uniregistrar.driver.did.btc1.DidBtc1Driver with given properties {}", () -> StringUtils.join(props));
	}

	private void initDriver() {

		log.info("Initializing the DID BTC1 Driver...");

		// Add a shutdown hook
		Runtime.getRuntime().addShutdownHook(new Thread(this::shutDown));

		log.debug("Open wallet services...");

		CompletableFuture<Boolean> openMainnet = null;
		CompletableFuture<Boolean> openTestnet = null;
		CompletableFuture<Boolean> openRegtest = null;
	}

	private void shutDown() {

		log.info("Performing cleanup of DID BTC1 Driver shutdown...");

		if (utxoWalletMainnet != null) {
			try {
				Context.propagate(contextMainnet);
				utxoWalletMainnet.saveToFile(utxoWalletMainnetFile);
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}

		if (utxoWalletTestnet != null) {
			try {
				Context.propagate(contextTestnet);
				utxoWalletTestnet.saveToFile(utxoWalletTestnetFile);
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}

		if (utxoWalletRegtestnet != null) {
			try {
				Context.propagate(contextRegtest);
				utxoWalletRegtestnet.saveToFile(utxoWalletRegtestnetFile);
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
	}
}
