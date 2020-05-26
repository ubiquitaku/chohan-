package ubiquitaku.chohan;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class Chohan extends JavaPlugin {
    String pln = "丁半plugin";
    List<Player> cho = new ArrayList<>();
    List<Player> han = new ArrayList<>();
    int money;
    boolean game;

    @Override
    public void onEnable() {
        // Plugin startup logic
        game = false;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(command.getName().equals("ch"))) {
            return true;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(pln + "コンソールからの実行ができないコマンドです");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(pln + "/ch <金額>　: 丁半を開始します");
            sender.sendMessage(pln + "/ch c : 丁に賭けます");
            sender.sendMessage(pln + "/ch h : 半に賭けます");
            return true;
        }

        if (game){
            if (args[0] == "c") {
                /*所持金の判定
                if (所持金 <= money) {
                    return true;
                }
                金を取り上げる
                */
                cho.add((Player) sender);
                han.remove(sender);
                sender.sendMessage("あなたは丁に" + money + "円賭けました");
                for (int i = 0; i < cho.size();i++) {
                    Player j = getServer().getPlayer(String.valueOf(cho.get(i)));
                    j.sendMessage(sender.getName() + "が丁に賭けました");
                    j.sendMessage(ChatColor.RED + "丁" + cho.size());
                    j.sendMessage(ChatColor.BLUE + "半" + han.size());
                }
                for (int i = 0; i < han.size();i++) {
                    Player j = getServer().getPlayer(String.valueOf(han.get(i)));
                    j.sendMessage(sender.getName() + "が丁に賭けました");
                    j.sendMessage(ChatColor.RED + "丁" + cho.size());
                    j.sendMessage(ChatColor.BLUE + "半" + han.size());
                }
                return true;
            }
            if (args[0] == "h") {
                /*所持金の判定
                if (所持金 <= money) {
                    return true;
                }
                金を取り上げる
                */
                cho.remove(sender);
                han.add((Player) sender);
                sender.sendMessage(pln + "あなたは半に" + money + "円賭けました");
                for (int i = 0; i < cho.size();i++) {
                    Player j = getServer().getPlayer(String.valueOf(cho.get(i)));
                    j.sendMessage(pln);
                    j.sendMessage(sender.getName() + "が半に賭けました");
                    j.sendMessage(ChatColor.RED + "丁" + cho.size());
                    j.sendMessage(ChatColor.BLUE + "半" + han.size());
                }
                for (int i = 0; i < han.size();i++) {
                    Player j = getServer().getPlayer(String.valueOf(han.get(i)));
                    j.sendMessage(pln);
                    j.sendMessage(sender.getName() + "が半に賭けました");
                    j.sendMessage(ChatColor.RED + "丁" + cho.size());
                    j.sendMessage(ChatColor.BLUE + "半" + han.size());
                }
                return true;
            }
            return true;
        }
        try{
            money = Integer.parseInt(args[0]);
        } catch (NumberFormatException money) {
            sender.sendMessage(pln + "開発者のミス出なければコマンドに間違いがあります");
            sender.sendMessage("/bd でコマンドのヘルプを確認することができます");
            return true;
        }
        Bukkit.broadcastMessage(pln + sender + "が" + money + "円の丁半を開始しました");
        game = true;
        //15秒のタイマー開始 カウントのラスト5秒はチャットに表示　カウント終了後結果を表示すると同時にgame=false

        Bukkit.getScheduler().runTaskTimer(this, new Runnable()
        {
            int time = 10; //or any other number you want to start countdown from

            @Override
            public void run() {
                if (this.time == 0) {
                    return;
                }
//                for (final Player player : Bukkit.getOnlinePlayers()) {
//                    player.sendMessage(this.time + " second(s) remains!");
//                }
                this.time--;
            }
        }, 0L, 20L);

        Bukkit.getScheduler().runTaskTimer(this, new Runnable()
        {
            int time = 5; //or any other number you want to start countdown from

            @Override
            public void run() {
                if (this.time == 0) {
                    return;
                }
                for (final Player player : Bukkit.getOnlinePlayers()) {
                    Bukkit.broadcastMessage(pln + "残り" + this.time + "秒");
                }
                this.time--;
            }
        }, 0L, 20L);
        game = false;
        Random rnd = new Random();
        int dice = rnd.nextInt((2)) + 1;
        if (dice == 1) {
            Bukkit.broadcastMessage(pln + "丁の勝利");
            for (int i = 0; i < cho.size(); i++) {
                Player c = getServer().getPlayer(String.valueOf(cho.get(i)));
                Bukkit.broadcastMessage(String.valueOf(c));
                //cに金を配布
            }
        } else if (dice == 2) {
            Bukkit.broadcastMessage(pln + "半の勝利");
            for (int i = 0; i < han.size(); i++) {
                Player c = getServer().getPlayer(String.valueOf(han.get(i)));
                Bukkit.broadcastMessage(String.valueOf(c));
                //hに金を配布
            }
        }
        return true;
    }
}
