package queue;

public interface Queue {
    // let save(head, tail, st):
    // (forall i = head..tail-1) this.elements`[(st + i) % a`.length] = this.elements[(st + i) % a.length]

    // Pre: element != null
    // Post: Если a - число элементов в очереди до операции, а a` - после, то:
    // a` = a + 1
    // save(head, tail, head`)
    // this.elements`[tail`] = element

    void enqueue(Object element);

    // Pre: element != null
    // Post: Если a - число элементов в очереди до операции, а a` - после, то:
    // a` = a + 1
    // save(head, tail, head`)
    // elements`[head`] = element
    void push(Object element);

    // Pre: element != null && size() > position >= 0
    // Post: Если a - число элементов в очереди до операции, а a` - после, то:
    // a` = a
    // saveExceptOne(head, tail, head`, position, element)
    // elements`[head`] = element
    void set(int position, Object element);

    // Пусть a - число элементов в очереди до операции, а a` - после
    // Pre: a > 0
    // Post:
    // a` = a - 1
    // save(head + 1, tail`, head`)
    // R = первый элемент в очереди
    Object dequeue();

    // Pre: a > 0
    // Post: Пусть a - число элементов в очереди до операции, а a` - после
    // a` = a - 1
    // save(head + 1, tail`, head`)
    // R = первый элемент в очереди
    Object remove();

    // Пусть a - число элементов в очереди до операции, а a` - после
    // Pre: a > 0
    // Post: a` = a
    // this.elements` = this.elements
    // R = первый элемент в очереди
    Object element();

    // Пусть a - число элементов в очереди до операции, а a` - после
    // Pre: a > 0
    // Post: a` = a
    // elements` = elements
    // R = последний элемент в очереди
    Object peek();

    // Пусть a - число элементов в очереди до операции, а a` - после
    // Pre: a > 0 && this.size() > index >= 0
    // Post: a` = a
    // this.elements` = this.elements
    // R = элемент c номером index считая от головы
    Object get(int index);

    // Если a - число элементов в очереди
    // Pre: true
    // Post: R = a
    int size();

    // Если a - число элементов в очереди
    // Pre: true
    // Post: R = (a == 0)
    boolean isEmpty();

    // Пусть a - число элементов в очереди до операции, а a` - после
    // Pre: true
    // Post: a` = 0, head` = 0, tail` = 0
    void clear();

    // Пусть a - число элементов в очереди до операции, а a` - после
    // Pre: true
    // Post: a` = a && все элементы очереди сохранены и имеют тот же порядок, что и до операции
    // && R = new Queue(каждый n-й элемент исходной очереди)
    Queue getNth(int n);

    // Pre: true
    // Post: все элементы очереди, кроме каждого n-ого сохранены и имеют тот же порядок, что и до операции
    // && каждый n-й элемент удалён из очереди
    // && R = new Queue(каждый n-й элемент исходной очереди)
    Queue removeNth(int n);

    // Pre: true
    // Post: все элементы очереди, кроме каждого n-ого сохранены и имеют тот же порядок, что и до операции
    // && каждый n-й элемент удалён из очереди
    void dropNth(int n);
}
