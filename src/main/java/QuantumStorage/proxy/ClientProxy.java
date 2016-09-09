package QuantumStorage.proxy;


import QuantumStorage.init.ModelHelper;

/**
 * Created by Gigabit101 on 09/09/2016.
 */
public class ClientProxy extends CommonProxy
{
    @Override
    public void registerRenders()
    {
        ModelHelper.init();
    }
}
