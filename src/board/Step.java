package board;

import java.io.Serializable;

public abstract class Step implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 43L;
	@Override
    public String toString()
    {
        return ToString();
    }
    public abstract String ToString();
}
