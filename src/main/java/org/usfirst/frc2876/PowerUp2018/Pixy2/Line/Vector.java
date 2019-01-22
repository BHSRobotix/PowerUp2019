package org.usfirst.frc2876.PowerUp2018.Pixy2.Line;

public class Vector {
    public int m_x0;
    public int m_y0;
    public int m_x1;
    public int m_y1;
    public int m_index;
    public int m_flags;

    public void print() {
        String str = String.format("vector: (%d %d) (%d %d) index: %d flags %d", m_x0, m_y0, m_x1, m_y1, m_index,
                m_flags);
        System.out.println(str);
    }
}