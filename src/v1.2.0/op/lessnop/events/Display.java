package op.lessnop.events;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Display {
   
    private String pakiet;

    public Display(String text) {
        this.pakiet = text;
    }
   
    public void sendToPlayer(Player p) {
        p.sendMessage(pakiet);
    }
   
    public void sendToAll() {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.sendMessage(pakiet);
        }
    }

}
