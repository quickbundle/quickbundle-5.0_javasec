package org.quickbundle.mda.gc.project;

import java.util.Map;

import org.eclipse.swt.widgets.Canvas;

public interface IConfigProject {
	void draw(Canvas canvasProject, Map<String, String> projectMap);
	Map<String, String> extractProjectMap();
	public String validate();
}
