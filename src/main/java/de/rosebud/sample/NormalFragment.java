package de.rosebud.sample;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import de.rosebud.core.Fragment;

@Service("normal")
@Scope("prototype")
public class NormalFragment extends Fragment{

	public NormalFragment() {
		super();
	}

	public NormalFragment(String startTemplate){
		super(startTemplate);
	}

}
