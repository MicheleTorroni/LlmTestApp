package llmTests.shared.java;

public class Pair<X,Y> {
	
	private final X fst;
	private final Y snd;
	public Pair(X fst, Y snd) {
		super();
		this.fst = fst;
		this.snd = snd;
	}
	public X getFst() {
		return fst;
	}
	public Y getSnd() {
		return snd;
	}
	public X getX() {return fst;}
	public Y getY() {return snd;}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fst == null) ? 0 : fst.hashCode());
		result = prime * result + ((snd == null) ? 0 : snd.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Pair))
			return false;
		Pair other = (Pair) obj;
		if (fst == null) {
			if (other.fst != null)
				return false;
		} else if (!fst.equals(other.fst))
			return false;
		if (snd == null) {
			if (other.snd != null)
				return false;
		} else if (!snd.equals(other.snd))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "[" + fst + ";" + snd + "]";
	}
	
	

}
