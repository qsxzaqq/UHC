package cc.i9mc.uhc.databse;

import cc.i9mc.gameutils.BukkitGameUtils;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
    public static PlayerData getPlayerData(String name) {
        PlayerData returnValue = null;

        try {
            Connection connection = BukkitGameUtils.getInstance().getConnectionPoolHandler().getConnection("uhcstats");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM uhc_stats_players Where Name=?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                returnValue = new PlayerData(name, resultSet.getString("kit"), resultSet.getInt("kills"), resultSet.getInt("wins"));
            } else {
                returnValue = new PlayerData(name, null, 0, 0);
            }

            preparedStatement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnValue;
    }

    public static void savePlayerData(PlayerData playerData) {
        try {
            Connection connection = BukkitGameUtils.getInstance().getConnectionPoolHandler().getConnection("uhcstats");
            if (connection == null || connection.isClosed()) {
                return;
            }

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM uhc_stats_players Where Name=?");
            preparedStatement.setString(1, playerData.getName());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                preparedStatement = connection.prepareStatement("INSERT INTO uhc_stats_players (Name,kit,kills,deaths,wins,loses,games) VALUES (?,?,?,?,?,?,?)");
                preparedStatement.setString(1, playerData.getName());
                preparedStatement.setString(2, playerData.getKit());
                preparedStatement.setInt(3, playerData.getKills());
                preparedStatement.setInt(4, playerData.getDeaths());
                preparedStatement.setInt(5, playerData.getWins());
                preparedStatement.setInt(6, playerData.getLoses());
                preparedStatement.setInt(7, playerData.getGames());
                preparedStatement.executeUpdate();
            } else {
                preparedStatement = connection.prepareStatement("UPDATE uhc_stats_players SET kit=?,kills=?,deaths=?,wins=?,loses=?,games=? Where Name=?");
                preparedStatement.setString(1, playerData.getKit());
                preparedStatement.setInt(2, resultSet.getInt("kills") + playerData.getKills());
                preparedStatement.setInt(3, resultSet.getInt("deaths") + playerData.getDeaths());
                preparedStatement.setInt(4, resultSet.getInt("wins") + playerData.getWins());
                preparedStatement.setInt(5, resultSet.getInt("loses") + playerData.getLoses());
                preparedStatement.setInt(6, resultSet.getInt("games") + playerData.getGames());
                preparedStatement.setString(7, playerData.getName());
                preparedStatement.executeUpdate();
            }

            preparedStatement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
