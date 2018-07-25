package test;

import java.lang.reflect.Field;

public class DesignTest {


	Class<?> bestFSClass;
	Class<?> cacheManagerClass;
	Class<?> cacheManagerInterface;
	Class<?> clientHandlerClass;
	Class<?> clientHandlerInterface;
	Class<?> pipeGameClass;
	Class<?> searchableInterface;
	Class<?> searcherInterface;
	Class<?> serverClass;
	Class<?> serverInteface;
	Class<?> solverClass;
	Class<?> solverInterface;
	
	
	public Class<?> getBestFSClass() {
		return bestFSClass;
	}
	public Class<?> getPipeGameClass() {
		return pipeGameClass;
	}
	public Class<?> getSearchableInterface() {
		return searchableInterface;
	}
	public Class<?> getSearcherInterface() {
		return searcherInterface;
	}
	public void setBestFSClass(Class<?> bestFSClass) {
		this.bestFSClass = bestFSClass;
	}
	public void setCacheManagerClass(Class<?> cacheManagerClass) {
		this.cacheManagerClass = cacheManagerClass;
	}
	public void setCacheManagerInterface(Class<?> cacheManagerInterface) {
		this.cacheManagerInterface = cacheManagerInterface;
	}
	public void setClientHandlerClass(Class<?> clientHandlerClass) {
		this.clientHandlerClass = clientHandlerClass;
	}
	
	
	public void setClientHandlerInterface(Class<?> clientHandlerInterface) {
		this.clientHandlerInterface = clientHandlerInterface;
	}
	
	
	public void setPipeGameClass(Class<?> pipeGameClass) {
		this.pipeGameClass = pipeGameClass;
	}
	
	public void setSearchableInterface(Class<?> searchableInterface) {
		this.searchableInterface = searchableInterface;
	}
	public void setSearcherInterface(Class<?> searcherInterface) {
		this.searcherInterface = searcherInterface;
	}
	public void setServerClass(Class<?> serverClass) {
		this.serverClass = serverClass;
	}
	public void setServerInteface(Class<?> serverInteface) {
		this.serverInteface = serverInteface;
	}
	public void setSolverClass(Class<?> solverClass) {
		this.solverClass = solverClass;
	}
	public void setSolverInterface(Class<?> solverInterface) {
		this.solverInterface = solverInterface;
	}
	public void testDesign() {
		testImp(serverInteface,serverClass,4);
		testImp(clientHandlerInterface,clientHandlerClass,4);
		testImp(cacheManagerInterface,cacheManagerClass,4);
		testImp(solverInterface,solverClass,4);
		int i=0;
		boolean ar[]={false,false};
		for( Field f : clientHandlerClass.getDeclaredFields()){
			if(f.getType().equals(solverInterface) || f.getType().equals(cacheManagerInterface)){
				ar[i]=true;
				i++;
			}
		}
		
		if(!ar[0] || !ar[1])
			System.out.println("Your ClientHandler does not contain a Solver or a CacheManager (-8)");
	}
	private void testImp(Class<?> inter, Class<?> imp,int points){
		if(!inter.isInterface())
			System.out.println("Your "+inter+" is not an interface (-"+points+")");
		if(!(imp.getInterfaces().length==1 && imp.getInterfaces()[0].equals(inter) ))
				System.out.println("Your "+imp+" does not implement "+inter+" or it has more than one interface (-"+points+")");
		
	}
	
}
