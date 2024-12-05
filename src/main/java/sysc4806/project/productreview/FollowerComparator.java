package sysc4806.project.productreview;

class FollowerComparator implements java.util.Comparator<Customer>{
    @Override
    public int compare(Customer c1, Customer c2) {
        return c1.getFollower_Count() - c2.getFollower_Count();
    }
}
