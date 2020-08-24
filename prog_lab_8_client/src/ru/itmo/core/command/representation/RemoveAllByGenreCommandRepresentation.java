package ru.itmo.core.command.representation;



public class RemoveAllByGenreCommandRepresentation extends CommandRepresentation {


    public RemoveAllByGenreCommandRepresentation() {
        super(
                "remove_all_by_genre",
                "Command: remove_all_by_genre <genre>" +
                        "\nDescription: Removes all the elements, which field 'genre' value matches the specified." +
                        "\nNumber of arguments: 1" +
                        "\n   Argument: genre (MusicGenre)" +
                        "\n",
                true,
                false
        );
    }
}
