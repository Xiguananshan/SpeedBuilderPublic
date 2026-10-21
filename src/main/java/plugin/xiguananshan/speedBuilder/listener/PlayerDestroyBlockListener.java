package plugin.xiguananshan.speedBuilder.listener;

import org.bukkit.*;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.block.BlockDamageEvent;

import org.bukkit.inventory.ItemStack;
import plugin.xiguananshan.speedBuilder.ToolClass;



public class PlayerDestroyBlockListener implements Listener {
    @EventHandler
    public void run(BlockDamageEvent e){

            Player p = e.getPlayer();
            if(ToolClass.exercise_timer.containsKey(p)){
                int index = ToolClass.exercise_player.get(p);
                int x = e.getBlock().getX(),y = e.getBlock().getY(),z = e.getBlock().getZ();
                if(!(4+30*index<=x&&x<=10+30*index&&151<=y&&y<=157&&4<=z&&z<=10)){

                    return;
                }
                if(e.getPlayer().getGameMode() != GameMode.SURVIVAL){
                    return;
                }

                if(!ToolClass.destroy_tick.containsKey(p)){

                    boolean isDoubleSlab = e.getBlock().getBlockData() instanceof Slab
                            && ((Slab) e.getBlock().getBlockData()).getType() == Slab.Type.DOUBLE;
                    BlockData blockData = e.getBlock().getBlockData();
                    if(isDoubleSlab){
                        p.getInventory().addItem(new ItemStack(e.getBlock().getType(),2));
                    }else{
                        if(blockData instanceof Bed b){
                            if(b.getPart() == Bed.Part.HEAD){
                                p.getInventory().addItem(new ItemStack(e.getBlock().getType(),1));
                            }
                        }else if(blockData instanceof Bisected b){
                            if(blockData instanceof Stairs || blockData instanceof TrapDoor || blockData instanceof SmallDripleaf){
                                p.getInventory().addItem(new ItemStack(e.getBlock().getType(),1));
                            }else{
                                if(b.getHalf() == Bisected.Half.BOTTOM){
                                    p.getInventory().addItem(new ItemStack(e.getBlock().getType(),1));
                                }
                            }

                        }else{
                            p.getInventory().addItem(new ItemStack(e.getBlock().getType(),1));
                        }

                    }


                    Location particleLocation = e.getBlock().getLocation().add(0.5, 0.5, 0.5);

                    SoundGroup soundGroup = e.getBlock().getBlockData().getSoundGroup();
                    e.getBlock().getWorld().spawnParticle(
                            Particle.BLOCK,
                            particleLocation,
                            20,
                            0.2, 0.2, 0.2,
                            0.1,
                            blockData
                    );
                    Sound breakSound = soundGroup.getBreakSound();
                    e.getBlock().getWorld().playSound(e.getBlock().getLocation(), breakSound, 1.0f, 1.0f);
                    e.getBlock().setType(Material.AIR);
                    ToolClass.destroy_tick.put(p,1);
                    e.setCancelled(true);
                }
            }else{
                if(!ToolClass.playing_player.contains(p)) return;
                int index = ToolClass.playing_player.indexOf(p);
                int x = e.getBlock().getX(),y = e.getBlock().getY(),z = e.getBlock().getZ();
                if(!(4+30*index<=x&&x<=10+30*index&&201<=y&&y<=207&&4<=z&&z<=10)){
                    return;
                }
                if(!ToolClass.is_building){
                    return;
                }
                if(e.getPlayer().getGameMode() != GameMode.SURVIVAL){
                    return;
                }






                if(!ToolClass.destroy_tick.containsKey(p)){


                    boolean isDoubleSlab = e.getBlock().getBlockData() instanceof Slab
                            && ((Slab) e.getBlock().getBlockData()).getType() == Slab.Type.DOUBLE;
                    BlockData blockData = e.getBlock().getBlockData();
                    if(isDoubleSlab){
                        p.getInventory().addItem(new ItemStack(e.getBlock().getType(),2));
                    }else{
                        if(blockData instanceof Bed b){
                            if(b.getPart() == Bed.Part.HEAD){
                                p.getInventory().addItem(new ItemStack(e.getBlock().getType(),1));
                            }
                        }else if(blockData instanceof Bisected b){
                            if(blockData instanceof Stairs || blockData instanceof TrapDoor || blockData instanceof SmallDripleaf){
                                p.getInventory().addItem(new ItemStack(e.getBlock().getType(),1));
                            }else{
                                if(b.getHalf() == Bisected.Half.BOTTOM){
                                    p.getInventory().addItem(new ItemStack(e.getBlock().getType(),1));
                                }
                            }

                        }else{
                            p.getInventory().addItem(new ItemStack(e.getBlock().getType(),1));
                        }

                    }


                    Location particleLocation = e.getBlock().getLocation().add(0.5, 0.5, 0.5);

                    SoundGroup soundGroup = e.getBlock().getBlockData().getSoundGroup();
                    e.getBlock().getWorld().spawnParticle(
                            Particle.BLOCK,
                            particleLocation,
                            20,
                            0.2, 0.2, 0.2,
                            0.1,
                            blockData
                    );
                    Sound breakSound = soundGroup.getBreakSound();
                    e.getBlock().getWorld().playSound(e.getBlock().getLocation(), breakSound, 1.0f, 1.0f);
                    e.getBlock().setType(Material.AIR);
                    ToolClass.destroy_tick.put(p,1);
                    e.setCancelled(true);
                }
            }

        }


}
