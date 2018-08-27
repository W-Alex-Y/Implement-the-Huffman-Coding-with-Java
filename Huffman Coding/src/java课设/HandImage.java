package java课设;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;
import java.util.Vector;

public class HandImage extends JFrame {
	private int x = 0;
	private int y = 0;
	private int d = 60;
	private int cnt = 0;
	private int length;
	private Tree t = new Tree();
	private JButton bNext = new JButton("下一步");
	private Vector<Node> nodes;
	
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
		g.dispose();
	}
	
	public HandImage(Vector<Node> n, List<Tree> T, Integer l) {
		nodes = n;
		length = l;
		JPanel jp = new JPanel();
		this.add(jp);
		this.setLocation(0, 0);
		this.setSize(1400, 800);
		this.setVisible(true);
		this.add(bNext, BorderLayout.SOUTH);
		bNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String label = bNext.getText();
				if(label.equals("下一步")) {
					t = T.get(cnt++);
					Graphics g = jp.getGraphics();
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
				} else if(label.equals("完成")) {
					setVisible(false);
				}
				if(cnt==length-1) {
					bNext.setText("完成");
				}
			}
		});
	}
	
}
