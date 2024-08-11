package br.com.caju.auth.strategy;

import java.util.HashMap;
import java.util.Map;

public class BalanceStrategyFactory {

    private static final Map<String, BalanceStrategy> STRATEGY_MAP = new HashMap<>();

    static {
        STRATEGY_MAP.put("5411", new FoodBalanceStrategy());
        STRATEGY_MAP.put("5412", new FoodBalanceStrategy());
        STRATEGY_MAP.put("5811", new MealBalanceStrategy());
        STRATEGY_MAP.put("5812", new MealBalanceStrategy());
    }

    public static BalanceStrategy getStrategy(String mcc) {
        return STRATEGY_MAP.getOrDefault(mcc, new CashBalanceStrategy());
    }
}