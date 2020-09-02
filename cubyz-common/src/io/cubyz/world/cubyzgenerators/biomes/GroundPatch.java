package io.cubyz.world.cubyzgenerators.biomes;

import java.util.Random;

import io.cubyz.blocks.Block;
import io.cubyz.world.MetaChunk;
import io.cubyz.world.ReducedChunk;
import io.cubyz.world.World;

// A small oval of different ground terrain.

public class GroundPatch extends StructureModel implements ReducedStructureModel {
	Block newGround;
	float width, variation, depth, smoothness;
	
	public GroundPatch(Block newGround, float chance, float width, float variation, float depth, float smoothness) {
		super(chance);
		this.newGround = newGround;
		this.width = width;
		this.variation = variation;
		this.depth = depth;
		this.smoothness = smoothness;
	}

	@Override
	public void generate(int x, int z, int h, Block[][][] chunk, float[][] heightMap, Random rand) {
		float width = this.width + (rand.nextFloat() - 0.5f)*this.variation;
		float orientation = 2*(float)Math.PI*rand.nextFloat();
		float ellipseParam = 1 + rand.nextFloat(); 

		// Orientation of the major and minor half axis of the ellipse.
		// For now simply use a minor axis 1/ellipseParam as big as the major.
		float xMain = (float)Math.sin(orientation)/width;
		float zMain = (float)Math.cos(orientation)/width;
		float xSecn = ellipseParam*(float)Math.cos(orientation)/width;
		float zSecn = -ellipseParam*(float)Math.sin(orientation)/width;
		int xMin = (int)(x - width);
		if(xMin < 0) xMin = 0;
		int xMax = (int)(x + width);
		if(xMax >= 16) xMax = 15;
		int zMin = (int)(z - width);
		if(zMin < 0) zMin = 0;
		int zMax = (int)(z + width);
		if(zMax >= 16) zMax = 15;
		for(int px = xMin; px <= xMax; px++) {
			for(int pz = zMin; pz <= zMax; pz++) {
				float main = xMain*(x - px) + zMain*(z - pz);
				float secn = xSecn*(x - px) + zSecn*(z - pz);
				float dist = main*main + secn*secn;
				if(dist <= 1) {
					for(int i = 0; i < depth; i++) {
						if(dist <= smoothness || (dist - smoothness)/(1 - smoothness) < rand.nextFloat())
							chunk[px][pz][(int)heightMap[px+8][pz+8] - i] = newGround;
					}
				}
			}
		}
	}

	@Override
	public void generate(int x, int z, int height, ReducedChunk chunk, MetaChunk metaChunk, Random rand) {
		float width = (this.width + (rand.nextFloat() - 0.5f)*this.variation)/(1 << chunk.resolution);
		float orientation = 2*(float)Math.PI*rand.nextFloat();
		float ellipseParam = 1 + rand.nextFloat(); 
		
		int chunkWidth = 16 >>> chunk.resolution;

		// Orientation of the major and minor half axis of the ellipse.
		// For now simply use a minor axis 1/ellipseParam as big as the major.
		float xMain = (float)Math.sin(orientation)/width;
		float zMain = (float)Math.cos(orientation)/width;
		float xSecn = ellipseParam*(float)Math.cos(orientation)/width;
		float zSecn = -ellipseParam*(float)Math.sin(orientation)/width;
		int xMin = (int)(x - width);
		if(xMin < 0) xMin = 0;
		int xMax = (int)(x + width);
		if(xMax >= chunkWidth) xMax = chunkWidth - 1;
		int zMin = (int)(z - width);
		if(zMin < 0) zMin = 0;
		int zMax = (int)(z + width);
		if(zMax >= chunkWidth) zMax = chunkWidth - 1;
		for(int px = xMin; px <= xMax; px++) {
			for(int pz = zMin; pz <= zMax; pz++) {
				float main = xMain*(x - px) + zMain*(z - pz);
				float secn = xSecn*(x - px) + zSecn*(z - pz);
				float dist = main*main + secn*secn;
				if(dist <= 1) {
					int index = (px << (4 - chunk.resolution)) | (((int)(metaChunk.heightMap[(px << chunk.resolution) + (chunk.cx << 4) & 255][(pz << chunk.resolution) + (chunk.cz << 4) & 255]*(World.WORLD_HEIGHT >>> chunk.resolution))) << (8 - 2*chunk.resolution)) | pz;
					if(dist <= smoothness || (dist - smoothness)/(1 - smoothness) < rand.nextFloat())
						chunk.blocks[index] = newGround.color;
				}
			}
		}
	}
}
