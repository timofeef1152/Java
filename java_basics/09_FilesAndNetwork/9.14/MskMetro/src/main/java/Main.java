import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final String SITE_URL = "data/wiki.html";
    private static final String FILE_PATH = "data/msk_map.json";

    public static void main(String[] args) {
        try {
            //Парсинг
            String html = Files.readString(Paths.get(SITE_URL));
            Document doc = Jsoup.parse(html);
            List<Line> lines = getLines(doc);
            //Создание и запись json объекта
            JSONObject jsonRoot = getJsonObject(lines);
            Files.writeString(Paths.get(FILE_PATH), jsonRoot.toJSONString());
            //Чтение json файла
            readJsonFile(FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Line> getLines(Document doc){
        List<Line> lines = new ArrayList<>();
        Element tbody = doc.select("table.standard > tbody").first();
        tbody.select("tr").stream().skip(1).forEach(row->{
            Elements cells = row.select("td");
            if (cells.size() < 3 || cells.get(1).text().contains("закрыта")) return;

            String lineStyle = cells.get(0).attr("style");
            String lineColor = lineStyle.substring(lineStyle.indexOf(":") + 1);
            Elements spansCell0 = cells.get(0).select("span.sortkey");
            if (spansCell0.size() == 0) return;

            spansCell0.remove(spansCell0.size() - 1);
            spansCell0.forEach(span -> {
                String lineNumber = span.text().startsWith("0") ? span.text().substring(1) : span.text();
                String lineName = span.nextElementSibling().attr("title");

                List<Connection> connections = new ArrayList<>();
                List<String> connectedLines = new ArrayList<>();
                Elements spansCell3 = cells.get(3).select("span.sortkey");
                spansCell3.forEach(s ->{
                    Element img = s.nextElementSibling().selectFirst("a > img");
                    String connectedLineNumber = s.text().startsWith("0") ? s.text().substring(1) : s.text();
                    String description = img.attr("alt");
                    Connection connection = new Connection(connectedLineNumber, description);
                    connections.add(connection);
                });
                Element link = cells.get(1).selectFirst("span > a");
                if (link == null){
                    link = cells.get(1).selectFirst("a");
                }

                Optional<Line> optionalLine = lines.stream().filter(l->l.getNumber().equalsIgnoreCase(lineNumber)).findFirst();
                Line line;
                if (optionalLine.isPresent()){
                    line = optionalLine.get();
                }
                else{
                    line = new Line(lineNumber, lineName, lineColor, new ArrayList<>());
                    lines.add(line);
                }

                String stationName = link.text();
                Station station = new Station(line, stationName, connections);
                line.getStationList().add(station);
            });
        });
        return lines;
    }

    private static JSONObject getJsonObject(List<Line> lines){
        JSONObject jsonStations = new JSONObject();
        lines.forEach(l->{
            JSONArray stationsArray = new JSONArray();
            stationsArray.addAll(l.getStationList().stream().map(Station::getName).collect(Collectors.toList()));
            jsonStations.put(l.getNumber(),stationsArray);
        });

        JSONArray jsonLines = new JSONArray();
        lines.forEach(l->{
            JSONObject linesArrayObj = new JSONObject();
            linesArrayObj.put("number", l.getNumber());
            linesArrayObj.put("name", l.getName());
            linesArrayObj.put("color", l.getColor());
            jsonLines.add(linesArrayObj);
        });

        JSONArray jsonConnections = new JSONArray();
        List<List<Station>> connectionsList = new ArrayList<>();
        List<Station> stations = new ArrayList<>();
        lines.forEach(l->stations.addAll(l.getStationList().stream().filter(s->s.getConnections().size() > 0).collect(Collectors.toList())));
        stations.forEach(s->{
            List<Station> connected = new ArrayList<>();
            connected.add(s);
            s.getConnections().forEach(c->{
                stations.stream()
                        .filter(s1->(s1.getLine().getNumber().equalsIgnoreCase(c.getLineNumber())) && c.getDescription().contains(s1.getName()))
                        .findFirst().ifPresent(connected::add);
            });
            if (connected.size() > 1 && connectionsList.stream().allMatch(list->connected.stream().anyMatch(s1->list.stream().noneMatch(s1::equals)))) {
                connectionsList.add(connected);
            }
        });

        connectionsList.forEach(list->{
            JSONArray array = new JSONArray();
            list.forEach(s->{
                JSONObject connection = new JSONObject();
                connection.put("line",s.getLine().getNumber());
                connection.put("station",s.getName());
                array.add(connection);
            });
            jsonConnections.add(array);
        });

        JSONObject jsonRoot = new JSONObject();
        jsonRoot.put("stations", jsonStations);
        jsonRoot.put("connections", jsonConnections);
        jsonRoot.put("lines", jsonLines);

        return jsonRoot;
    }

    private static void readJsonFile(String filePath) throws IOException {
        String text = Files.readString(Paths.get(filePath));
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonRoot = (JSONObject) parser.parse(text);
            JSONObject stationsObject = (JSONObject) jsonRoot.get("stations");
            stationsObject.keySet().forEach(k->{
                JSONArray stationsArray = (JSONArray) stationsObject.get(k);
                System.out.println("Линия №" + k.toString() + "; количество станций: " + stationsArray.size());
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
