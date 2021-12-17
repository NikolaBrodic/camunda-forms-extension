package rs.ac.uns.ftn.vehicleinspectionapp.enums;

public enum FuelType {
    PETROL("Petrol"),
    DIESEL("Diesel"),
    LPG("LPG"),
    CNG("CNG"),
    ELECTRIC("Electric");

    private String displayName;

    FuelType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
