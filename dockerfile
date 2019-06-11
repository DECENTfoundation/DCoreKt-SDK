FROM decentnetwork/dcore.ubuntu:test

COPY --chown=dcore:dcore ./test-dcore-node/datadir /home/dcore/.decent/data/decentd
COPY --chown=dcore:dcore ./test-dcore-node/genesis-local.json /home/dcore/.decent/genesis.json
