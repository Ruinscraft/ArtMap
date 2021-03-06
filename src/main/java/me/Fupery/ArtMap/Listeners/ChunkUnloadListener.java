package me.Fupery.ArtMap.Listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.ChunkUnloadEvent;

import me.Fupery.ArtMap.ArtMap;
import me.Fupery.ArtMap.Easel.Easel;
import me.Fupery.ArtMap.Easel.EaselMap;
import me.Fupery.ArtMap.Utils.ChunkLocation;

class ChunkUnloadListener implements RegisteredListener {

    @EventHandler
    public void onChunkUnload(final ChunkUnloadEvent event) {
        EaselMap easels = ArtMap.getEasels();
        if (!easels.isEmpty() && event.getChunk() != null) {
            ChunkLocation chunk = new ChunkLocation(event.getChunk());
            ArtMap.getScheduler().ASYNC.run(() -> {
                for (Location location : easels.keySet()) {
                    Easel easel = easels.get(location);
					if (easel != null && easel.getChunk() != null && easel.getChunk().equals(chunk)) {
                        easels.remove(location);
                    }
                }
            });
        }
    }

    @Override
    public void unregister() {
        ChunkUnloadEvent.getHandlerList().unregister(this);
    }
}
