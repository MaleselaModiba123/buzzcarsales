package za.ac.cput.dto.response;

public record CarMakeResponse(
        Integer makeId,
        String makeName,
        String originCountry
) {
}
