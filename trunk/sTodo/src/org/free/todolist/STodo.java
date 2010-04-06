package org.free.todolist;

import java.util.List;

import javax.script.ScriptContext;
import javax.swing.SwingUtilities;

import org.free.todolist.plugin.Plugin;
import org.free.todolist.plugin.PluginManager;
import org.free.todolist.plugin.TodoPlugin;
import org.free.todolist.plugin.TodoPluginManager;
import org.free.todolist.ui.MainFrame;

/**
 * the main entry of sTodo
 * 
 * @author juntao.qiu@gmail.com
 *
 */
public class STodo {
	private MainFrame mainFrame;
	
	public STodo(MainFrame frame){
		this.mainFrame = frame;
	}

	public static void initEnv(){
		PluginManager pManager = TodoPluginManager.getInstance();
		Plugin system = 
			new TodoPlugin("scripts/system.js", "system", "system initialize");
		Plugin menubar = 
			new TodoPlugin("scripts/menubar.js", "menubar", "application menubar");
		pManager.install(system);
		pManager.install(menubar);
		
//		ScriptContext context = initContext();
	}
	
	public ScriptContext initContext(){
	    return null;
	}
	
	public void activePlugin(String scriptFile){
		Plugin newPlugin = new TodoPlugin(scriptFile, scriptFile, scriptFile);
		TodoPluginManager.getInstance().install(newPlugin);
	}
	
	public List<Plugin> getPluginList(){
		return TodoPluginManager.getInstance().listPlugins();
	}
	
	public MainFrame getUI(){
		return mainFrame;
	}
	
	public void launch(){
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				mainFrame.initUI();
			}
		});
	}
	
	public static void main(String[] args){
		STodo.initEnv();
		
		STodo sTodo = new STodo(new MainFrame("My todo list"));
		
		Plugin system = TodoPluginManager.getInstance().getPlugin("system");
		system.putValueToContext("application", sTodo);
		system.execute("_init_", new Object());
	}
}
