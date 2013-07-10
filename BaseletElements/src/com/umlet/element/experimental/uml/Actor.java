package com.umlet.element.experimental.uml;

import DefaultGlobalFacet.DefaultGlobalTextFacet.ElementStyleEnum;

import com.baselet.control.enumerations.AlignHorizontal;
import com.baselet.control.enumerations.AlignVertical;
import com.baselet.diagram.draw.BaseDrawHandler;
import com.baselet.diagram.draw.geom.XValues;
import com.baselet.element.StickingPolygon;
import com.umlet.element.experimental.ElementId;
import com.umlet.element.experimental.NewGridElement;
import com.umlet.element.experimental.Properties;
import com.umlet.element.experimental.settings.Settings;
import com.umlet.element.experimental.settings.facets.DefaultGlobalFacet;
import com.umlet.element.experimental.settings.facets.Facet;
import com.umlet.element.experimental.settings.facets.SeparatorLine;

public class Actor extends NewGridElement {
	
	private class SettingsActor extends Settings {

		@Override
		public XValues getXValues(double y, int height, int width) {
			return new XValues(0, width);
		}

		@Override
		public AlignVertical getVAlign() {
			return AlignVertical.TOP;
		}

		@Override
		public AlignHorizontal getHAlign() {
			return AlignHorizontal.CENTER;
		}

		@Override
		public ElementStyleEnum getElementStyle() {
			return ElementStyleEnum.AUTORESIZE;
		}

		@Override
		public Facet[] createFacets() {
			return new Facet[]{new SeparatorLine()};
		}
		
		@Override
		public Facet[] createGlobalFacets() {
			return new Facet[]{new DefaultGlobalFacet()};
		}
		
		@Override
		public double getYPosStart() {
			return headToLegLength(); // equals headBodyLegLength
		}
		
		@Override
		public double getMinElementWidthForAutoresize() {
			return armLength()*2; // armLength
		}
	}

	@Override
	public ElementId getId() {
		return ElementId.UMLActor;
	}
	
	@Override
	protected void updateConcreteModel(BaseDrawHandler drawer, Properties properties) {
		int hCenter = getRealSize().width/2;
		drawer.drawCircle(hCenter, headRadius(), headRadius()); // Head
		drawer.drawLine(hCenter-armLength(), armHeight(), hCenter+armLength(), armHeight()); // Arms
		drawer.drawLine(hCenter, headRadius()*2, hCenter, headToBodyLength()); // Body
		drawer.drawLine(hCenter, headToBodyLength(), hCenter-legSpan(), headToLegLength()); // Legs
		drawer.drawLine(hCenter, headToBodyLength(), hCenter+legSpan(), headToLegLength()); // Legs
		properties.drawPropertiesText();
	}

	@Override
	public StickingPolygon generateStickingBorder(int x, int y, int width, int height) {
		int hCenter = getRealSize().width/2;
		
		StickingPolygon p = new StickingPolygon();
		p.addPoint((int)(hCenter-armLength()), 0);
		p.addPoint((int)(hCenter+armLength()), 0);
		p.addPoint((int)(hCenter+armLength()), (int)(headToLegLength()));
		p.addPoint((int)(hCenter-armLength()), (int)(headToLegLength()), true);
		return p;
	}

	@Override
	public Settings getSettings() {
		return new SettingsActor();
	}

	private double headToLegLength() {
		return legSpan()*2+headToBodyLength();
	}

	private double legSpan() {
		return getDrawer().getCurrentStyle().getFontSize();
	}

	private double headToBodyLength() {
		return getDrawer().getCurrentStyle().getFontSize()*2+headRadius()*2;
	}

	private double armHeight() {
		return armLength();
	}
	
	private double armLength() {
		return getDrawer().getCurrentStyle().getFontSize()*1.5;
	}

	private double headRadius() {
		return getDrawer().getCurrentStyle().getFontSize()/2;
	}
}

