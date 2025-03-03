package prisoncore.aDragz.Features.Pve.Events.Mobs;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import prisoncore.aDragz.Features.Tokens.Commands.tokenGive;

import java.util.EventListener;
import java.util.Objects;

public class mobKill implements EventListener, Listener {
    
    @EventHandler
    public void entityKillEvent (EntityDeathEvent event) {
        
        if (event.getEntity().getKiller() instanceof Player) {
            
            if (event.getEntityType().toString().equalsIgnoreCase("zombie")) {
                if (Objects.isNull(event.getEntity().getCustomName())) {
                    String[] args = new String[]{"none", "none", "250"};
                    tokenGive.giveToken(null, event.getEntity().getKiller(), args); //token give {name}
                } else if (event.getEntity().getCustomName().contains("Basic")) {
                    String[] args = new String[]{"none", "none", "1000"};
                    tokenGive.giveToken(null, event.getEntity().getKiller(), args); //token give {name}
                } else if (event.getEntity().getCustomName().contains("Leather")) {
                    String[] args = new String[]{"none", "none", "2000"};
                    tokenGive.giveToken(null, event.getEntity().getKiller(), args); //token give {name}
                }
            } else if (event.getEntityType().toString().equalsIgnoreCase("skeleton")) {
                if (Objects.isNull(event.getEntity().getCustomName())) {
                    String[] args = new String[]{"none", "none", "500"};
                    tokenGive.giveToken(null, event.getEntity().getKiller(), args); //token give {name}
                } else if (event.getEntity().getCustomName().contains("Leather")) {
                    String[] args = new String[]{"none", "none", "10000"};
                    tokenGive.giveToken(null, event.getEntity().getKiller(), args); //token give {name}
                } else if (event.getEntity().getCustomName().contains("Golden")) {
                    String[] args = new String[]{"none", "none", "15000"};
                    tokenGive.giveToken(null, event.getEntity().getKiller(), args); //token give {name}
                }
            } else if (event.getEntityType().toString().equalsIgnoreCase("spider")) {
                String[] args = new String[]{"none", "none", "1000"};
                tokenGive.giveToken(null, event.getEntity().getKiller(), args); //token give {name}
            }  else if (event.getEntityType().toString().equalsIgnoreCase("enderman")) {
                String[] args = new String[]{"none", "none", "10000"};
                tokenGive.giveToken(null, event.getEntity().getKiller(), args); //token give {name}
            } else if (event.getEntityType().toString().equalsIgnoreCase("creeper")) {
                String[] args = new String[]{"none", "none", "750"};
                tokenGive.giveToken(null, event.getEntity().getKiller(), args); //token give {name}
            }
        }
    }
}