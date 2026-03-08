package pro1;

public class Shared {
    public static String ProcessLine(String line)
    {
        var separator = detectSeparator(line);

        String name = line.split(separator)[0];
        String fractionStr = line.split(separator)[1];

        var frac = Fraction.parse(fractionStr);

        return name + ";" + frac.toString();
    }

    private static String detectSeparator(String line)
    {
        if (line.split(";").length > 1) {
            return ";";
        }
        else if (line.split(",").length > 1) {
            return ",";
        }
        else if (line.split("\\|").length > 1) {
            return "\\|";
        }
        else if (line.split(":").length > 1) {
            return ":";
        }
        else if (line.split("=").length > 1) {
            return "=";
        }
        else {
            throw new RuntimeException("Unknown separator in line: " + line);
        }
    }
}
