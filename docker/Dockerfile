# Dockerfile for universalregistrar/driver-did-btc1

FROM maven:3-jdk-11 AS BUILD
MAINTAINER Markus Sabadello <markus@danubetech.com>

ENV PATH $JAVA_HOME/bin:$PATH

# build driver-did-btc1

ADD . /opt/driver-did-btc1
RUN cd /opt/driver-did-btc1 && mvn clean install package -P war -N -DskipTests

FROM jetty:9.4-jre11-slim

# variables

ENV uniregistrar_driver_did_btc1_Mainnet=
ENV uniregistrar_driver_did_btc1_Testnet=
ENV uniregistrar_driver_did_btc1_Regtest=

ENV uniregistrar_driver_did_btc1_methodPrefix=
ENV uniregistrar_driver_did_btc1_testnetPrefix=
ENV uniregistrar_driver_did_btc1_mainnetPrefix=
ENV uniregistrar_driver_did_btc1_regtestPrefix=

ENV uniregistrar_driver_did_btc1_basePath=/opt/btc1-continuation
ENV uniregistrar_driver_did_btc1_baseUri=http://localhost

ENV uniregistrar_driver_did_btc1_privateKeyMainnet=
ENV uniregistrar_driver_did_btc1_privateKeyTestnet=
ENV uniregistrar_driver_did_btc1_privateKeyRegtest=

ENV uniregistrar_driver_did_btc1_walletPathMainnet=/opt/wallets
ENV uniregistrar_driver_did_btc1_walletPathTestnet=/opt/wallets
ENV uniregistrar_driver_did_btc1_walletPathRegtest=/opt/wallets

ENV uniregistrar_driver_did_btc1_walletPrefixMainnet=mainnet
ENV uniregistrar_driver_did_btc1_walletPrefixTestnet=testnet
ENV uniregistrar_driver_did_btc1_walletPrefixRegtest=regtestnet

ENV uniregistrar_driver_did_btc1_requiredTxDepth=
ENV uniregistrar_driver_did_btc1_targetFundAmount=

ENV uniregistrar_driver_did_btc1_utxoUpkeepMainnet=
ENV uniregistrar_driver_did_btc1_utxoUpkeepTestnet=
ENV uniregistrar_driver_did_btc1_utxoUpkeepRegtest=

ENV uniregistrar_driver_did_btc1_utxoProducingMainnet=false
ENV uniregistrar_driver_did_btc1_utxoProducingTestnet=true
ENV uniregistrar_driver_did_btc1_utxoProducingRegtest=true

ENV uniregistrar_driver_did_btc1_checkIntervalMainnet=
ENV uniregistrar_driver_did_btc1_checkIntervalTestnet=
ENV uniregistrar_driver_did_btc1_checkIntervalRegtest=

ENV uniregistrar_driver_did_btc1_rpcUrlMainnet=
ENV uniregistrar_driver_did_btc1_rpcUrlTestnet=
ENV uniregistrar_driver_did_btc1_rpcUrlRegtest=

ENV uniregistrar_driver_did_btc1_peerMainnet=
ENV uniregistrar_driver_did_btc1_peerTestnet=
ENV uniregistrar_driver_did_btc1_bitcoinConnection=BTCD

ENV uniregistrar_driver_did_btc1_followDeactivation=true
ENV uniregistrar_driver_did_btc1_overWriteDidDocs=true

ENV uniregistrar_driver_did_btc1_rpcCertMainnet=
ENV uniregistrar_driver_did_btc1_rpcCertTestnet=

# copy from build stage

COPY --from=build /opt/driver-did-btc1/target/*.war /var/lib/jetty/webapps/ROOT.war
COPY --from=build /opt/driver-did-btc1/docker/run-driver-did-btc1.sh /opt

# done

EXPOSE 9080

USER root
RUN chmod a+rx /opt/run-driver-did-btc1.sh
CMD "/opt/run-driver-did-btc1.sh"