package java课设;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;
import java.util.Vector;

public class AutoImage extends JFrame {
	private int x = 0;
	private int y = 0;
	private int d = 60;
	private int T = 100;
	private JButton bClose = new JButton("完成");
	private Vector<Node> nodes;
	private List<Tree> trees;
	
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		for(Node i : nodes) {
			x = i.xNode;
			y = i.yNode;
			g.setFont(new Font("Arial", Font.BOLD, 30));
			g.setColor(this.getBackground());
			g.fillOval(x, y, d, d);
		}
		for(Tree t : trees) {
			try {
				Thread.sleep(T);
			} catch (InterruptedException e) {
			}
			for(Node i : nodes) {
				if(t.getParents() == i.getNode()) {
					x = i.xNode;
					y = i.yNode;
					g.setFont(new Font("Arial", Font.BOLD, 30));
					g.setColor(Color.black);
					g.drawString(i.sNode, x, y);
					g.fillOval(x, y, d, d);
					for(Node j : nodes) {
						if(t.getLchild().equals(j.getNode()) || t.getRchild().equals(j.getNode())) {
							x = j.xNode;
							y = j.yNode;
							g.setFont(new Font("Arial", Font.BOLD, 30));
							g.setColor(Color.black);
							g.drawString(j.sNode, x, y);
							g.fillOval(x, y, d, d);
							g.drawLine(i.xNode+d/2, i.yNode+d/2, j.xNode+d/2, j.yNode+d/2);
						}
					}
				}
			}
		}
		g.dispose();
	}
	
	public AutoImage(Vector<Node> n, List<Tree> t, Integer time) {
		JPanel jp = new JPanel();
		this.add(jp);
		nodes = n;
		trees = t;
		T = time;
		this.add(bClose, BorderLayout.SOUTH);
		this.setLocation(0, 0);
		bClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		this.setSize(1400, 800);
		this.setVisible(true);
	}
	
}
