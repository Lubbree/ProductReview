package sysc4806.project.productreview;

class JaccardComparator implements java.util.Comparator<Customer>{
    @Override
    public int compare(Customer c1, Customer c2) {
        double s1 = c1.getJaccard_Index() * 100;
        double s2 = c2.getJaccard_Index() * 100;
        return (int) (s1 - s2);
    }
}
