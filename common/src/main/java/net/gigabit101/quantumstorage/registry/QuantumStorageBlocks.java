package net.gigabit101.quantumstorage.registry;

import java.util.List;

public final class QuantumStorageBlocks {
    public static final QuantumStorageBlockEntry QUANTUM_TANK = entry("quantum_tank");
    public static final QuantumStorageBlockEntry CRATER = entry("crater");
    public static final QuantumStorageBlockEntry CHEST_IRON = entry("chest_iron");
    public static final QuantumStorageBlockEntry CHEST_GOLD = entry("chest_gold");
    public static final QuantumStorageBlockEntry CHEST_DIAMOND = entry("chest_diamond");
    public static final QuantumStorageBlockEntry QUANTUM_STORAGE_UNIT = entry("quantum_storage_unit");
    public static final QuantumStorageBlockEntry QUANTUM_CRAFTER = entry("quantumcrafter");
    public static final QuantumStorageBlockEntry TRASH_CAN = entry("trashcan");
    public static final QuantumStorageBlockEntry TRASH_CAN_FLUID = entry("trashcanfluid");
    public static final QuantumStorageBlockEntry CHEST_QUANTUM = entry("chest_quantum");
    public static final QuantumStorageBlockEntry MULTISTORAGE = entry("multistorage");

    public static final List<QuantumStorageBlockEntry> ALL = List.of(
            QUANTUM_TANK,
            CRATER,
            CHEST_IRON,
            CHEST_GOLD,
            CHEST_DIAMOND,
            QUANTUM_STORAGE_UNIT,
            QUANTUM_CRAFTER,
            TRASH_CAN,
            TRASH_CAN_FLUID,
            CHEST_QUANTUM,
            MULTISTORAGE
    );

    private QuantumStorageBlocks() {
    }

    private static QuantumStorageBlockEntry entry(String id) {
        return new QuantumStorageBlockEntry(id, "block.quantumstorage." + id);
    }
}
