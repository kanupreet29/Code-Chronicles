package View;

public class ColourScheme {

    public String colourSchemeName; // Colour Scheme folder name without spaces for path to images in colour scheme folder.
    String backgroundColour;
    String regularFontColour;
    String headingsFontColour;
    String buttonColour1;
    String buttonColour2;

    public ColourScheme (String colourScheme) {
        if (colourScheme.equals("Monochrome")) {
            this.colourSchemeName = "Monochrome";
            this.backgroundColour = "#1A1A1A";
            this.regularFontColour = "#FFFFFF";
            this.headingsFontColour = "#FFFFFF";
            this.buttonColour1 = "#4F4F4F";
            this.buttonColour2 = "#333333";
        } else { // default is "Game Theme"
            this.colourSchemeName = "GameTheme";
            this.backgroundColour = "#210776";
            this.regularFontColour = "#FFFFFF";
            this.headingsFontColour = "#5CE1E6";
            this.buttonColour1 = "#4264D4";
            this.buttonColour2 = "#8C52FF";
        }
    }

}
