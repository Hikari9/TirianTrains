package tiriantrains;

// holds information about a train station
public class Station {
    
    // Members
    protected final String name;
    
    // Accessors
    public String getName() { return name; }
    public String getTownName() { return TirianTrains.getTown(getName()); }
    @Override
    public String toString() { return getName() + " (" + getTownName() + ")"; }
    
    // Constructor
    public Station(String name) { this.name = name; }
    
}
