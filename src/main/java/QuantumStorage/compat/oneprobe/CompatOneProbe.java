package QuantumStorage.compat.oneprobe;

import mcjty.theoneprobe.TheOneProbe;

/**
 * Created by Gigabit101 on 10/10/2016.
 */
public class CompatOneProbe
{
    public static void init()
    {
        TheOneProbe.theOneProbeImp.registerProvider(new ProbeProvider());
    }
}
