// ============================================
// CODING ARENA — MAIN JS
// ============================================

// Auto-dismiss alerts after 4 seconds
document.addEventListener('DOMContentLoaded', () => {
  document.querySelectorAll('.alert').forEach(alert => {
    setTimeout(() => {
      alert.style.transition = 'opacity 0.5s';
      alert.style.opacity = '0';
      setTimeout(() => alert.remove(), 500);
    }, 4000);
  });

  // Line numbers for code editor
  const editor = document.getElementById('codeEditor');
  const lineNumbers = document.getElementById('lineNumbers');
  if (editor && lineNumbers) {
    const updateLineNumbers = () => {
      const lines = editor.value.split('\n').length;
      lineNumbers.innerHTML = Array.from({ length: lines }, (_, i) => i + 1).join('<br>');
    };
    editor.addEventListener('input', updateLineNumbers);
    editor.addEventListener('scroll', () => {
      lineNumbers.scrollTop = editor.scrollTop;
    });

    // Tab key support
    editor.addEventListener('keydown', e => {
      if (e.key === 'Tab') {
        e.preventDefault();
        const start = editor.selectionStart;
        const end = editor.selectionEnd;
        editor.value = editor.value.substring(0, start) + '    ' + editor.value.substring(end);
        editor.selectionStart = editor.selectionEnd = start + 4;
        updateLineNumbers();
      }
    });

    // Set default code templates on language change
    const langSelect = document.getElementById('langSelect');
    const templates = {
      JAVA: `import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Your code here

    }
}`,
      PYTHON: `import sys
input = sys.stdin.readline

def solve():
    # Your code here
    pass

solve()`,
      CPP: `#include <bits/stdc++.h>
using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    // Your code here

    return 0;
}`
    };

    if (langSelect) {
      // Set initial template
      if (!editor.value.trim()) {
        editor.value = templates['JAVA'];
        updateLineNumbers();
      }

      langSelect.addEventListener('change', () => {
        if (confirm('Switch language? Your current code will be replaced with a template.')) {
          editor.value = templates[langSelect.value] || '';
          updateLineNumbers();
        }
      });
    }

    updateLineNumbers();
  }

  // Confirm delete
  document.querySelectorAll('[data-confirm]').forEach(el => {
    el.addEventListener('click', e => {
      if (!confirm(el.dataset.confirm)) e.preventDefault();
    });
  });
});
