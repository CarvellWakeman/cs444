public class Resource
{
    private int m_val = 0;

    public Resource(){}

    public int GetValue()
    {
        return m_val;
    }
    public void IncValue()
    {
        m_val += 1;
    }
}