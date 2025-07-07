package app.BasketballStats.dto;

public record CombinedUpdateDTO(
    Long playerId,
    Long gameId,
    Long statsId,
    String playerName,
    String position,
    Integer number,
    String opponent,
    String gameDate,
    int twoPScored,
    int twoPShot,
    int threePScored,
    int threePShot,
    int ftScored,
    int ftShot
) {}
