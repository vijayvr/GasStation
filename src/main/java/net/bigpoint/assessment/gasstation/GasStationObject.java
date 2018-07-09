package net.bigpoint.assessment.gasstation;

import java.util.*;

import net.bigpoint.assessment.gasstation.exceptions.GasTooExpensiveException;
import net.bigpoint.assessment.gasstation.exceptions.GasTypeNotProvidedException;
import net.bigpoint.assessment.gasstation.exceptions.NotEnoughGasException;


public class GasStationObject implements GasStation {
    // Members
    private List<GasPump> _gasPumps = new ArrayList<GasPump>();
    private Map<GasType, Double> _prices = new HashMap<GasType, Double>() {{
        put(GasType.REGULAR, 1.0);
        put(GasType.SUPER, 1.1);
        put(GasType.DIESEL, 1.2);
    }};
    private double _revenue = 0;
    private int _numberOfSales = 0;
    private int _numberOfGasTypeNotFound = 0;
    private int _numberOfCancellationsNoGas = 0;
    private int _numberOfCancellationsTooExpensive = 0;


    public GasStationObject() {

    }

    public void addGasPump(GasPump gasPump) {
        _gasPumps.add(gasPump);
    }

    public double buyGas(GasType type, double amountInLiters, double maxPricePerLiter) throws NotEnoughGasException, GasTooExpensiveException, GasTypeNotProvidedException {

        List<GasPump> gasType = new ArrayList<GasPump>();

        // Check which pumps provides the requested gas type
        GasPump gasPump = null;
        for (GasPump t : _gasPumps) {
            if (t.getGasType().equals(type)) {
                gasPump = t;
                break;
            }
        }
        // Check if there is any pump with the requested  gas type
        if (gasPump != null) {
            // Gas type is available
            // Check if gas is sold for requested price or lower
            if (_prices.get(type) <= maxPricePerLiter) {
                //Check if pump provides enough gas
                if (gasPump.getRemainingAmount() >= amountInLiters) {
                    // Enough gas can be provided
                    gasPump.pumpGas(amountInLiters);
                    double price = amountInLiters * _prices.get(type);
                    _revenue += price;
                    _numberOfSales++;
                    return price;
                } else {
                    // No pump has enough gas
                    _numberOfCancellationsNoGas++;
                    throw new NotEnoughGasException();
                }
            } else {
                // Gas is too expensive
                _numberOfCancellationsTooExpensive++;
                throw new GasTooExpensiveException();
            }
        } else {
            //gas type not found
            _numberOfGasTypeNotFound++;
            throw new GasTypeNotProvidedException();
        }
    }

    public Collection<GasPump> getGasPumps() {
        return _gasPumps;
    }

    public double getRevenue() {
        return _revenue;
    }

    public int getNumberOfSales() {
        return _numberOfSales;
    }

    public int getNumberOfCancellationsNoGas() {
        return _numberOfCancellationsNoGas;
    }

    public int getNumberOfCancellationsTooExpensive() {
        return _numberOfCancellationsTooExpensive;
    }

    public double getPrice(GasType type) {
        return _prices.get(type);
    }

    public void setPrice(GasType type, double price) {
        if (_prices.containsKey(type)) {
            _prices.put(type, price);
        }
    }

}