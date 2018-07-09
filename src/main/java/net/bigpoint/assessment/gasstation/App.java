package net.bigpoint.assessment.gasstation;

import net.bigpoint.assessment.gasstation.exceptions.GasTooExpensiveException;
import net.bigpoint.assessment.gasstation.exceptions.GasTypeNotProvidedException;
import net.bigpoint.assessment.gasstation.exceptions.NotEnoughGasException;

public class App {

    public static void main(String[] args) {
        GasStation obj = new GasStationObject();
        //adding data to database
        GasPump gp = new GasPump(GasType.DIESEL,100);
        obj.addGasPump(gp);

        try {
           double v=  obj.buyGas(GasType.DIESEL, 50,1.3);
            System.out.println(v);
        } catch (NotEnoughGasException e) {
            e.printStackTrace();
        } catch (GasTooExpensiveException e) {
            e.printStackTrace();
        } catch (GasTypeNotProvidedException e) {
            e.printStackTrace();
        }
    }
}
