package de.rosebud.core;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("normal")
@Scope("prototype")
public class NormalFragment extends Fragment{

}
