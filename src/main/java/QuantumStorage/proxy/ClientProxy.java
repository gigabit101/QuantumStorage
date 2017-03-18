package QuantumStorage.proxy;

import QuantumStorage.init.ModelHandler;

/**
 * Created by Gigabit101 on 07/03/2017.
 */
public class ClientProxy extends CommonProxy
{
    @Override
    public void registerRenders()
    {
        ModelHandler.init();
    }
}
