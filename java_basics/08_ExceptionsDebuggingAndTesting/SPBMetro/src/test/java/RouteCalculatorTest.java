import core.Line;
import core.Station;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class RouteCalculatorTest extends TestCase {

    List<Station> routeOnTheLine;
    List<Station> routeWithOneConnection;
    List<Station> routeWithTwoConnections;

    Line red;
    Line blue;
    Line green;

    Station gorkovskaya;
    Station nevskiyProspect;
    Station gostiniyDvor;
    Station mayakovskaya;
    Station ploshadVosstaniya;
    Station chernishevskaya;

    StationIndex stationIndex;
    RouteCalculator calculator;

    private List<Station> getRouteOnTheLine(){
        List<Station> route = new ArrayList<>();
        route.add(gorkovskaya);
        route.add(nevskiyProspect);
        return route;
    }

    private List<Station> getRouteWithOneConnection(){
        List<Station> route = new ArrayList<>();
        route.add(gorkovskaya);
        route.add(nevskiyProspect);
        route.add(gostiniyDvor);
        route.add(mayakovskaya);
        return route;
    }

    private List<Station> getRouteWithTwoConnections(){
        List<Station> route = new ArrayList<>();
        route.add(gorkovskaya);
        route.add(nevskiyProspect);
        route.add(gostiniyDvor);
        route.add(mayakovskaya);
        route.add(ploshadVosstaniya);
        route.add(chernishevskaya);
        return route;
    }

    private void initializeLines(){
        red = new Line(1,"Красная");
        blue = new Line(2,"Синяя");
        green = new Line(3,"Зеленая");
    }

    private void initializeStations(){
        gorkovskaya = new Station("Горьковская", blue);
        nevskiyProspect = new Station("Невский проспект", blue);
        gostiniyDvor = new Station("Гостиный двор", green);
        mayakovskaya = new Station("Маяковская", green);
        ploshadVosstaniya = new Station("Буревестник", red);
        chernishevskaya = new Station("Чернышевская", red);
    }

    private void addStationsToLines(){
        blue.addStation(gorkovskaya);
        blue.addStation(nevskiyProspect);
        green.addStation(gostiniyDvor);
        green.addStation(mayakovskaya);
        red.addStation(ploshadVosstaniya);
        red.addStation(chernishevskaya);
    }

    private void initializeStationIndex(){
        stationIndex = new StationIndex();
        routeOnTheLine.forEach(station -> stationIndex.addStation(station));
        routeWithOneConnection.forEach(station -> stationIndex.addStation(station));
        routeWithTwoConnections.forEach(station -> stationIndex.addStation(station));
        addConnections();
        stationIndex.addLine(blue);
        stationIndex.addLine(green);
        stationIndex.addLine(red);
    }

    private void addConnections(){
        List<Station> connectedStationsBlueGreen = new ArrayList<>();
        connectedStationsBlueGreen.add(nevskiyProspect);
        connectedStationsBlueGreen.add(gostiniyDvor);

        List<Station> connectedStationsGreenRed = new ArrayList<>();
        connectedStationsGreenRed.add(mayakovskaya);
        connectedStationsGreenRed.add(ploshadVosstaniya);

        stationIndex.addConnection(connectedStationsBlueGreen);
        stationIndex.addConnection(connectedStationsGreenRed);
    }

    private boolean areEqualsRoutes(List<Station> route1, List<Station> route2){
        if (route1.size() != route2.size()){
            return false;
        }
        boolean expected = false;
        for (int i = 0; i < route1.size(); i++) {
            expected = route1.get(i).equals(route2.get(i));
            if (!expected){
                break;
            }
        }
        return expected;
    }

    @Override
    protected void setUp() throws Exception {
        initializeLines();
        initializeStations();
        addStationsToLines();

        routeOnTheLine = getRouteOnTheLine();
        routeWithOneConnection = getRouteWithOneConnection();
        routeWithTwoConnections = getRouteWithTwoConnections();

        initializeStationIndex();
        calculator = new RouteCalculator(stationIndex);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetShortestRouteOnTheLine(){
        List<Station> actual = calculator.getShortestRoute(gorkovskaya, nevskiyProspect);
        List<Station> expectedRoute = new ArrayList<>();
        expectedRoute.add(gorkovskaya);
        expectedRoute.add(nevskiyProspect);
        boolean expected = areEqualsRoutes(actual,expectedRoute);
        assertTrue(expected);
    }

    public void testGetShortestRouteWithOneConnection(){
        List<Station> actual = calculator.getShortestRoute(gorkovskaya, mayakovskaya);
        List<Station> expectedRoute = new ArrayList<>();
        expectedRoute.add(gorkovskaya);
        expectedRoute.add(nevskiyProspect);
        expectedRoute.add(gostiniyDvor);
        expectedRoute.add(mayakovskaya);
        boolean expected = areEqualsRoutes(actual,expectedRoute);
        assertTrue(expected);
    }

    public void testGetShortestRouteWithTwoConnections(){
        List<Station> actual = calculator.getShortestRoute(gorkovskaya, chernishevskaya);
        List<Station> expectedRoute = new ArrayList<>();
        expectedRoute.add(gorkovskaya);
        expectedRoute.add(nevskiyProspect);
        expectedRoute.add(gostiniyDvor);
        expectedRoute.add(mayakovskaya);
        expectedRoute.add(ploshadVosstaniya);
        expectedRoute.add(chernishevskaya);
        boolean expected = areEqualsRoutes(actual,expectedRoute);
        assertTrue(expected);
    }

    public void testCalculateDurationRouteOnTheLine(){
        double actual = RouteCalculator.calculateDuration(routeOnTheLine);
        double expected = 2.5;
        assertEquals(expected,actual);
    }

    public void testCalculateDurationRouteWithOneConnection(){
        double actual = RouteCalculator.calculateDuration(routeWithOneConnection);
        double expected = 8.5;
        assertEquals(expected,actual);
    }

    public void testCalculateDurationRouteWithTwoConnections(){
        double actual = RouteCalculator.calculateDuration(routeWithTwoConnections);
        double expected = 14.5;
        assertEquals(expected,actual);
    }
}
