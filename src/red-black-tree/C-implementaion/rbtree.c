/* The authors of this work have released all rights to it and placed it
in the public domain under the Creative Commons CC0 1.0 waiver
(http://creativecommons.org/publicdomain/zero/1.0/).

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

Retrieved from: http://en.literateprograms.org/Red-black_tree_(C)?oldid=19567
*/

#include "rbtree.h"
#include <assert.h>
#include <stdlib.h>

typedef rbtree_node node;
typedef enum rbtree_node_color color;

static node grandparent(node n);

static node sibling(node n);

static node uncle(node n);

static void verify_properties(rbtree t);

static void verify_property_1(node root);

static void verify_property_2(node root);

static color node_color(node n);

static void verify_property_4(node root);

static void verify_property_5(node root);

static void verify_property_5_helper(node n, int black_count, int *black_count_path);

static node new_node(void *key, void *value, color node_color, node left, node right);

static node lookup_node(rbtree t, void *key, compare_func compare);

static void rotate_left(rbtree t, node n);

static void rotate_right(rbtree t, node n);

static void replace_node(rbtree t, node oldn, node newn);

static void insert_case1(rbtree t, node n);

static void insert_case2(rbtree t, node n);

static void insert_case3(rbtree t, node n);

static void insert_case4(rbtree t, node n);

static void insert_case5(rbtree t, node n);

static node maximum_node(node root);

static void delete_case1(rbtree t, node n);

static void delete_case2(rbtree t, node n);

static void delete_case3(rbtree t, node n);

static void delete_case4(rbtree t, node n);

static void delete_case5(rbtree t, node n);

static void delete_case6(rbtree t, node n);

node grandparent(node n) {
    assert (n != NULL);
    assert (n->parent != NULL); /* Not the root node */
    assert (n->parent->parent != NULL); /* Not child of root */
    return n->parent->parent;
}

node sibling(node n) {
    assert (n != NULL);
    assert (n->parent != NULL); /* Root node has no sibling */
    if (n == n->parent->left)
        return n->parent->right;
    else
        return n->parent->left;
}

node uncle(node n) {
    assert (n != NULL);
    assert (n->parent != NULL); /* Root node has no uncle */
    assert (n->parent->parent != NULL); /* Children of root have no uncle */
    return sibling(n->parent);
}

void verify_properties(rbtree t) {
#ifdef VERIFY_RBTREE
    verify_property_1(t->root);
     verify_property_2(t->root);
     /* Property 3 is implicit */
     verify_property_4(t->root);
     verify_property_5(t->root);
#endif
}

void verify_property_1(node n) {
    assert(node_color(n) == RED || node_color(n) == BLACK);
    if (n == NULL) return;
    verify_property_1(n->left);
    verify_property_1(n->right);
}

void verify_property_2(node root) {
    assert(node_color(root) == BLACK);
}

color node_color(node n) {
    return n == NULL ? BLACK : n->color;
}

void verify_property_4(node n) {
    if (node_color(n) == RED) {
        assert (node_color(n->left) == BLACK);
        assert (node_color(n->right) == BLACK);
        assert (node_color(n->parent) == BLACK);
    }
    if (n == NULL) return;
    verify_property_4(n->left);
    verify_property_4(n->right);
}

void verify_property_5(node root) {
    // black_count_path should be renamed to count_black_height
    int black_count_path = -1;
    verify_property_5_helper(root, 0, &black_count_path);
}

/**
 * we do it by traversing the tree, incrementing a black node count as we go.
 * The first time we reach a leaf we save the count. When we subsequently reach other leaves,
 * we compare the count to this saved count.
 *
 * @param n
 * @param black_count
 * @param path_black_count
 */
void verify_property_5_helper(node n, int black_count, int *path_black_count) {
    if (node_color(n) == BLACK) {
        black_count++;
    }
    if (n == NULL) {
        // The first time we reach a leaf we save the count
        if (*path_black_count == -1) {
            *path_black_count = black_count;
        } else { // when we subsequently reach other leaves, we compare the count to this saved count
            assert (black_count == *path_black_count);
        }
        return;
    }
    verify_property_5_helper(n->left, black_count, path_black_count);
    verify_property_5_helper(n->right, black_count, path_black_count);
}

rbtree rbtree_create() {
    rbtree t = malloc(sizeof(struct rbtree_t));
    t->root = NULL;
    verify_properties(t);
    return t;
}

node new_node(void *key, void *value, color node_color, node left, node right) {
    node result = malloc(sizeof(struct rbtree_node_t));
    result->key = key;
    result->value = value;
    result->color = node_color;
    result->left = left;
    result->right = right;
    if (left != NULL) left->parent = result;
    if (right != NULL) right->parent = result;
    result->parent = NULL;
    return result;
}

node lookup_node(rbtree t, void *key, compare_func compare) {
    node n = t->root;
    while (n != NULL) {
        int comp_result = compare(key, n->key);
        if (comp_result == 0) {
            return n;
        } else if (comp_result < 0) {
            n = n->left;
        } else {
            assert(comp_result > 0);
            n = n->right;
        }
    }
    return n;
}

void *rbtree_lookup(rbtree t, void *key, compare_func compare) {
    node n = lookup_node(t, key, compare);
    return n == NULL ? NULL : n->value;
}

void rotate_left(rbtree t, node n) {
    node r = n->right;
    replace_node(t, n, r);
    n->right = r->left;
    if (r->left != NULL) {
        r->left->parent = n;
    }
    r->left = n;
    n->parent = r;
}

void rotate_right(rbtree t, node n) {
    node L = n->left;
    replace_node(t, n, L);
    n->left = L->right;
    if (L->right != NULL) {
        L->right->parent = n;
    }
    L->right = n;
    n->parent = L;
}

void replace_node(rbtree t, node oldn, node newn) {
    if (oldn->parent == NULL) { // new root
        t->root = newn;
    } else {
        if (oldn == oldn->parent->left)
            oldn->parent->left = newn;
        else
            oldn->parent->right = newn;
    }
    if (newn != NULL) {
        newn->parent = oldn->parent;
    }
}

void rbtree_insert(rbtree t, void *key, void *value, compare_func compare) {
    node inserted_node = new_node(key, value, RED, NULL, NULL);
    if (t->root == NULL) {
        t->root = inserted_node;
    } else {
        node n = t->root;
        while (1) {
            int comp_result = compare(key, n->key);
            if (comp_result == 0) {
                n->value = value;
                return;
            } else if (comp_result < 0) {
                if (n->left == NULL) {
                    n->left = inserted_node;
                    break;
                } else {
                    n = n->left;
                }
            } else {
                assert (comp_result > 0);
                if (n->right == NULL) {
                    n->right = inserted_node;
                    break;
                } else {
                    n = n->right;
                }
            }
        }
        inserted_node->parent = n;
    }
    insert_case1(t, inserted_node);
    verify_properties(t);
}

void insert_case1(rbtree t, node n) {
    if (n->parent == NULL)
        // In this case, the new node is now the root node of the tree. Since the root node must be black,
        // and changing its color adds the same number of black nodes to every path, we simply recolor it black.
        n->color = BLACK;
    else
        // when the node has a parent =>
        insert_case2(t, n);
}

void insert_case2(rbtree t, node n) {
    if (node_color(n->parent) == BLACK)
        // In this case, the new node has a black parent. All the properties are still satisfied and we return.
        return; /* Tree is still valid */
    else
        // In this case, n's parent is red  =>
        insert_case3(t, n);
}

void insert_case3(rbtree t, node n) {
    if (node_color(uncle(n)) == RED) {
        // In this case, the uncle node is red. For parent and uncle, left and right are symmetrical.
        // We don't care whether n is parent's left child or right child.
        //
        // We recolor the parent and uncle black and the grandparent red.
        // However, the red grandparent node may now violate the red-black tree properties;
        // we recursively invoke this procedure on it from case 1 to deal with this.
        n->parent->color = BLACK;
        uncle(n)->color = BLACK;
        grandparent(n)->color = RED;

        // However, the grandparent G may now violate requirement 3, if it has a red parent.
        // After relabeling G to N the loop invariant is fulfilled so that the rebalancing can be iterated on one black level (= 2 tree levels) higher
        insert_case1(t, grandparent(n));
    } else {
        // The parent P is red but the uncle U is black =>
        insert_case4(t, n);
    }
}

void insert_case4(rbtree t, node n) {
    // Case 4: In this case, we deal with two cases that are mirror images of one another:
    //
    // - The new node is the right child of its parent and the parent is the left child of the grandparent.
    // In this case we rotate left about the parent.
    // - The new node is the left child of its parent and the parent is the right child of the grandparent.
    // In this case we rotate right about the parent.
    // Neither of these fixes the properties, but they put the tree in the correct form to apply case 5.
    if (n == n->parent->right && n->parent == grandparent(n)->left) {
        rotate_left(t, n->parent);
        n = n->left; // exchange P N label for higher iteration
    } else if (n == n->parent->left && n->parent == grandparent(n)->right) {
        rotate_right(t, n->parent);
        n = n->right; // exchange P N label for higher iteration
    }

    insert_case5(t, n);
}

void insert_case5(rbtree t, node n) {
    // Case 5: In this final case, we deal with two cases that are mirror images of one another:
    //
    // - The new node is the left child of its parent and the parent is the left child of the grandparent.
    // In this case we rotate right about the grandparent.
    // - The new node is the right child of its parent and the parent is the right child of the grandparent.
    // In this case we rotate left about the grandparent.
    // Now the properties are satisfied and all cases have been covered.
    n->parent->color = BLACK;
    grandparent(n)->color = RED;
    if (n == n->parent->left && n->parent == grandparent(n)->left) {
        rotate_right(t, grandparent(n));
    } else {
        assert (n == n->parent->right && n->parent == grandparent(n)->right);
        rotate_left(t, grandparent(n));
    }

    // Summary: note that inserting is actually in-place, since all the calls above use tail recursion.
    // Moreover, it performs at most two rotations, since the only recursive call(see also: insert_case3 => insert_case1)
    // occurs before making any rotations.
}

void rbtree_delete(rbtree t, void *key, compare_func compare) {
    node child;
    // We begin by finding the node to be deleted with lookup_node() and deleting it precisely as we would in a binary search tree
    node n = lookup_node(t, key, compare);
    if (n == NULL) return;  /* Key not found, do nothing */

    // There are two cases for removal, depending on whether the node to be deleted has at most one, or two non-leaf children.
    // A node with at most one non-leaf child can simply be replaced with its non-leaf child.

    // When deleting a node with two non-leaf children, we copy the value from the in-order predecessor
    // (the maximum or rightmost element in the left subtree) into the node to be deleted,
    // and then we then delete the predecessor node, which has only one non-leaf child(erratum: at most one non-leaf node)
    if (n->left != NULL && n->right != NULL) {
        /* Copy key/value from predecessor and then delete it instead */
        node pred = maximum_node(n->left);
        n->key = pred->key;
        n->value = pred->value;
        n = pred;
    }

    // make sure n is a node with at most one non-leaf child.
    assert(n->left == NULL || n->right == NULL);
    child = n->right == NULL ? n->left : n->right;

    // However, before deleting the node, we must ensure that doing so does not violate the red-black tree properties.
    // If the node we delete is black, and we cannot change its child from red to black to compensate,
    // then we would have one less black node on every path through the child node.
    // We must adjust the tree around the node being deleted to compensate.
    if (node_color(n) == BLACK) {
        // Question: why simplified to copy child color to n?
        // case: if child is red, delete case 1 -> 2-> 3-> 4, then return.
        // case: if child is black(nil), follow complex case analysis
        n->color = node_color(child);
        delete_case1(t, n);
    }
    replace_node(t, n, child);
    if (n->parent == NULL && child != NULL) // root should be black
        child->color = BLACK;
    free(n);

    verify_properties(t);
}

static node maximum_node(node n) {
    assert (n != NULL);
    while (n->right != NULL) {
        n = n->right;
    }
    return n;
}

// The important invariant that holds at this point is the following:
// the black node count on all paths through (the possibly "phantom") current, and only those paths through current,
// is short by one and there are no double red violations anywhere in the tree.
//
// The rules below either maintain the invariant as current rises in the tree or find a way to:
// increase the black node count on paths through current. Specifically,
// - either a black must be added to the path through current
// - or the black node count on all other paths must be reduced.

void delete_case1(rbtree t, node n) {
    if (n->parent == NULL)
        // Case 1: In this case, N has become the root node.
        // The deletion removed one black node from every path, so no properties are violated.
        return;
    else
        // n has parent =>
        delete_case2(t, n);
}

void delete_case2(rbtree t, node n) {
    if (node_color(sibling(n)) == RED) {
        // Case 2: N has a red sibling. => we can infer the fact:
        // N(black)  P(black) S(red) SL(black) SR(black)
        //
        // In this case we exchange the colors of the parent and sibling,
        // then rotate about the parent so that the sibling becomes the parent of its former parent.
        // **This does not restore the tree properties, but reduces the problem to one of the remaining cases.**

        n->parent->color = RED;
        sibling(n)->color = BLACK;
        if (n == n->parent->left)
            rotate_left(t, n->parent);
        else
            rotate_right(t, n->parent);

        // Consider why [This does not restore the tree properties]?
        // example: * means red
        //    50
        //      \
        //      60(p)
        //    /     \
        //  55(N)    66*(S)
        //          /     \
        //	    65(SL)  68(SR)

        //     50
        //       \
        //       66
        //     /     \
        //    60*    68
        //  /    \
        // 55(N)  65

        // when 55 is deleted later, the black height of path [60* -> 66 -> 50] is less one than other paths.
        // so this adjustment is incomplete.
        // It's impossible that 60* has only one black child 65.
    }

    delete_case3(t, n);
}

void delete_case3(rbtree t, node n) {
    if (node_color(n->parent) == BLACK &&
        node_color(sibling(n)) == BLACK &&
        node_color(sibling(n)->left) == BLACK &&
        node_color(sibling(n)->right) == BLACK) {

        // Case 3: In this case N's parent, sibling, and sibling's children are black.
        // In this case we paint the sibling red.
        sibling(n)->color = RED;

        // Now all paths passing through N's parent(P) have one less black node than before the deletion,
        // **so we must recursively run this procedure from case 1 on N's parent.**
        // in order to make sure paths that do not pass through P can also be balanced.
        delete_case1(t, n->parent);
    } else
        delete_case4(t, n);
}

void delete_case4(rbtree t, node n) {
    if (node_color(n->parent) == RED &&
        node_color(sibling(n)) == BLACK &&
        node_color(sibling(n)->left) == BLACK &&
        node_color(sibling(n)->right) == BLACK) {
        // Case 4: N's sibling and sibling's children are black, but its parent is red.
        // We exchange the colors of the sibling and parent; this restores the tree properties.
        sibling(n)->color = RED;
        n->parent->color = BLACK;
    } else
        delete_case5(t, n);
}

void delete_case5(rbtree t, node n) {
    // Case 5: There are two cases handled here which are mirror images of one another:
    //
    // - N's sibling S is black, S's left child is red, S's right child is black, and N is the left child of its parent.
    // We exchange the colors of S and its left sibling and rotate right at S.
    // - N's sibling S is black, S's right child is red, S's left child is black, and N is the right child of its parent.
    // We exchange the colors of S and its right sibling and rotate left at S.
    // Both of these function to reduce us to the situation described in case 6.
    if (n == n->parent->left &&
        node_color(sibling(n)) == BLACK &&
        node_color(sibling(n)->left) == RED &&
        node_color(sibling(n)->right) == BLACK) {
        sibling(n)->color = RED;
        sibling(n)->left->color = BLACK;
        rotate_right(t, sibling(n));
    } else if (n == n->parent->right &&
               node_color(sibling(n)) == BLACK &&
               node_color(sibling(n)->right) == RED &&
               node_color(sibling(n)->left) == BLACK) {
        sibling(n)->color = RED;
        sibling(n)->right->color = BLACK;
        rotate_left(t, sibling(n));
    }

    delete_case6(t, n);
}

void delete_case6(rbtree t, node n) {
    // Case 6: There are two cases handled here which are mirror images of one another:
    //
    // - N's sibling S is black, S's right child is red, and N is the left child of its parent.
    // We exchange the colors of N's parent and sibling, make S's right child black, then rotate left at N's parent.
    //
    // - N's sibling S is black, S's left child is red, and N is the right child of its parent.
    // We exchange the colors of N's parent and sibling, make S's left child black, then rotate right at N's parent.

    sibling(n)->color = node_color(n->parent);
    n->parent->color = BLACK;
    if (n == n->parent->left) {
        assert (node_color(sibling(n)->right) == RED);
        sibling(n)->right->color = BLACK;
        rotate_left(t, n->parent);
    } else {
        assert (node_color(sibling(n)->left) == RED);
        sibling(n)->left->color = BLACK;
        rotate_right(t, n->parent);
    }

    // This accomplishes three things at once:
    //
    // We add a black node to all paths through N, either by adding a black S to those paths or by recoloring N's parent black.
    // We remove a black node from all paths through S's red child, either by removing P from those paths or by recoloring S.
    // We recolor S's red child black, adding a black node back to all paths through S's red child.
}