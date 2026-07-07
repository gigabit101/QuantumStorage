package net.gigabit101.quantumstorage;

import net.gigabit101.quantumstorage.registry.QuantumStorageContent;

public final class QuantumStorageCommon {
    private QuantumStorageCommon() {
    }

    public static void init() {
        QuantumStorageContent.init();
        Constants.LOG.info("Initializing {}", Constants.MOD_NAME);
        Constants.LOG.info("Preparing {} shared block definitions", QuantumStorageContent.BLOCK_DEFINITION_COUNT);
    }
}
