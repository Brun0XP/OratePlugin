package com.github.brun0xp.spigotplugintemp.database;

import com.github.brun0xp.spigotplugintemp.Main;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class SQLite extends Database {

    private final String database;
    private final Main main;

    /**
     * Creates a new SQLite instance
     *
     * @param database Location of the Database (Must end in .db)
     */
    public SQLite(Main main, String database) {
        this.main = main;
        this.database = database;
    }

    @Override
    public Connection openConnection() throws SQLException,
            ClassNotFoundException {
        if (checkConnection()) {
            return connection;
        }

        File dataFolder = main.getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        File file = new File(dataFolder, database);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                main.getLogger().log(Level.SEVERE, "Unable to create database!", e);
            }
        }
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager
                .getConnection("jdbc:sqlite:"
                        + dataFolder + "/"
                        + database);
        return connection;
    }


}