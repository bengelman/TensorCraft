package com.bengelman.tensorcraft.tileentity;

import java.net.*;

import com.bengelman.tensorcraft.TensorCraft;

import java.io.*;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class TileEntityNeuralNetwork extends TileEntity implements ITickable{
	final int NN_NO_HANDSHAKE = -1;
	final int NN_NO_MODEL = 0;
	final int NN_RECEIVING_DATA = 1;
	final int NN_PYTHON_COMPUTING = 2;
	final int NN_RUNNING = 3;
	
	final int INPUTS = 4;
	final int OUTPUTS = 4;
	
	int currentState = NN_NO_HANDSHAKE;
	boolean powered = false;
	
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    
    int bottleneck = 0;
	public TileEntityNeuralNetwork() {
		super();
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)return;
		try {
			TileEntityNeuralNetwork tenn = this;
			new Thread(new Runnable() {
				public void run() {
					try {
						clientSocket = new Socket("127.0.0.1", 2323);
						out = new PrintWriter(clientSocket.getOutputStream(), true);
				        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				        currentState = NN_NO_MODEL;
				        out.write("2323\n");
				        out.flush();
				        System.out.println("Neural network connection established on port 2323");
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void update() {
		if (world.isRemote)return;
		if (currentState == NN_NO_HANDSHAKE) {
			return;
		}
		try {
			if (in.ready() && currentState == NN_RUNNING && powered) {
				bottleneck--;
				System.out.print("Reading line: ");
				String line = "";
				for (int i = 1; i <= OUTPUTS; i++)
					this.getWorld().setBlockState(this.getPos().add(i, 0, 2), (line=in.readLine()).equals("0") || (line.equals("-1")) ? Blocks.AIR.getDefaultState() : Blocks.REDSTONE_BLOCK.getDefaultState());
				
			}
			if (currentState == NN_RUNNING && bottleneck < 5 && powered) {
				bottleneck++;
				for (int i = 1; i <= INPUTS; i++) 
					out.write(this.getWorld().isBlockPowered(this.getPos().add(i, 0, 0)) ? "1\n" : "0\n");
				out.flush();
			}
			if (currentState == NN_RECEIVING_DATA) {
				for (int i = 1; i <= INPUTS; i++) 
					out.write(this.getWorld().isBlockPowered(this.getPos().add(i, 0, 0)) ? "1\n" : "0\n");
				for (int i = 1; i <= OUTPUTS; i++) 
					out.write(this.getWorld().isBlockPowered(this.getPos().add(i, 0, 1)) ? "1\n" : "0\n");
				out.flush();
			}
			if (in.ready() && currentState == NN_PYTHON_COMPUTING) {
				String response = in.readLine();
				System.out.println("Server reponse: " + response);
				if (response.equals("2323")) {
					System.out.println("Model has compiled");
					currentState = NN_RUNNING;
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		
	}
	public void setPowered(boolean power) {
		if (world.isRemote)return;
		this.powered = power;
		if (power) {
			if (currentState == NN_NO_MODEL) {
				System.out.println("Sending data to TensorFlow");
				currentState = NN_RECEIVING_DATA;
				out.write("2323\n");
				out.write("" + INPUTS + "\n");
				out.write("" + OUTPUTS + "\n");
				out.flush();
			}
		}
		else {
			if (currentState == NN_RECEIVING_DATA) {
				out.write("" + -1 + "\n");
				out.flush();
				currentState = NN_PYTHON_COMPUTING;
			}
		}
	}
	public void handleUpdateTag(NBTTagCompound nbt) {
		super.handleUpdateTag(nbt);
	}
	public NBTTagCompound getUpdateTag() {
		return super.getUpdateTag(); 
	}
}
