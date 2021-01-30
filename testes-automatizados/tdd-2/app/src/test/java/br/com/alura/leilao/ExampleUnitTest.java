package br.com.alura.leilao;

import org.junit.Test;

import br.com.alura.leilao.models.Usuario;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        assertNotEquals(3, 2 + 2);

        assertTrue(true);
        assertFalse(false);

        assertNull(null);
        Usuario alex = new Usuario("Alex");
        assertNotNull(alex);

        assertThat(2 + 2, equalTo(4));
    }
}