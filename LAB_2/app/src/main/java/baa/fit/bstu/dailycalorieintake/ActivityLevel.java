package baa.fit.bstu.dailycalorieintake;

public enum ActivityLevel {
    PASSIVE_LIFESTYLE("Passive lifestyle", 1.2),
    MODERATE_ACTIVITY("Moderate activity", 1.375),
    MEDIUM_ACTIVITY("Medium activity", 1.55),
    ACTIVE("Active", 1.725),
    ATHLETE_ACTIVITY("Athlete activity", 1.9);

    private String _name;
    private double _coefficient;

    ActivityLevel(String name, double coefficient) {
        _name = name;
        _coefficient = coefficient;
    }

    public double GetCoefficient() {
        return  _coefficient;
    }

    @Override
    public String toString() {
        return _name;
    }
}
