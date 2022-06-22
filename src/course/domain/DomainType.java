package course.domain;

import java.io.Serializable;

public abstract class DomainType implements Serializable {

	private static final long serialVersionUID = -7873357187538188983L;

	protected Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
