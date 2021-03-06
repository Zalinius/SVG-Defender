package com.zalinius.polygonpal;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import com.zalinius.zje.architecture.Graphical;
import com.zalinius.zje.architecture.Logical;
import com.zalinius.zje.physics.Locatable;
import com.zalinius.zje.physics.Point;
import com.zalinius.zje.physics.Vector;
import com.zalinius.zje.physics.Vertex;

public class Projectile implements Logical, Graphical, Locatable{
	private Vertex position;
	private final static double radius = 10;
	private boolean hit;
	
	private ProjectileStrategy strategy;
	
	public Projectile(Point center, Vector v0, ProjectileStrategy strat) {
		position = new Vertex(center, 2);
		position.impulse(v0.scale(position.mass()));
		hit = false;
		this.strategy = strat;
	}
	
	public boolean wasHit() {
		return hit;
	}
	
	public void hit() {
		hit = true;
	}

	public Ellipse2D.Double shape() {
		return new Ellipse2D.Double(position.x() - radius, position.y() - radius, 2*radius, 2*radius);
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(strategy.color());
		g.fill(shape());
		Shape shape = new Line2D.Double(position.x(), position.y(), position.x(), position.y());
		g.setColor(Color.WHITE);
		g.draw(shape);
	}

	@Override
	public void update(double delta) {
		Vector force = strategy.force(position);
		position.update(force, delta);		
	}

	@Override
	public Point position() {
		return position.position();
	}
	
	public Vector momentum() {
		return position.momentum();
	}
	
}
