package uniregistrar.driver.did.btc1.job;

import uniregistrar.driver.did.btc1.connections.bitcoin.BitcoinConnection;

import java.util.UUID;

public class Job {

    private String jobId;
    private BitcoinConnection bitcoinConnection;
    private Integer nextState;
    private String btc1Did;
    private String nymRequest;
    private String attribRequest;

    public Job(String jobId, BitcoinConnection bitcoinConnection) {
        this.jobId = jobId;
        this.bitcoinConnection = bitcoinConnection;
    }

    public Job(BitcoinConnection bitcoinConnection) {
        this(UUID.randomUUID().toString(), bitcoinConnection);
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public BitcoinConnection getBitcoinConnection() {
        return bitcoinConnection;
    }

    public void setBitcoinConnection(BitcoinConnection bitcoinConnection) {
        this.bitcoinConnection = bitcoinConnection;
    }

    public Integer getNextState() {
        return nextState;
    }

    public void setNextState(Integer nextState) {
        this.nextState = nextState;
    }

    public String getBtc1Did() {
        return btc1Did;
    }

    public void setBtc1Did(String btc1Did) {
        this.btc1Did = btc1Did;
    }
}
