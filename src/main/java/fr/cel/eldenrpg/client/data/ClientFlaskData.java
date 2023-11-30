package fr.cel.eldenrpg.client.data;

public class ClientFlaskData {

    private static int playerFlasks;

    public static void set(int flasks) {
        ClientFlaskData.playerFlasks = flasks;
    }

    public static int getPlayerFlasks() {
        return playerFlasks;
    }

}