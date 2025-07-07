package app.BasketballStats.dto;

public record CombinedInsertDTO(
    String playerName,
    String position,
    Integer number,
    int twoPScored,
    int twoPShot,
    int threePScored,
    int threePShot,
    int ftScored,
    int ftShot,
    String gameDate,
    String opponent
) {}
