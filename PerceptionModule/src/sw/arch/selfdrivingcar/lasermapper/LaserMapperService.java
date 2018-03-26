package sw.arch.selfdrivingcar.lasermapper;

import java.util.Date;

public interface LaserMapperService {

	void deriveEnvironmentMap(String name, double x, double y, double z, Date timestamp);

}